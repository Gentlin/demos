#include<iostream>
#include<list>
#include<condition_variable>
#include<mutex>
#include<atomic>
template<typename T>
class SyncQueue {
public:
		SyncQueue(int maxSize = 1024):mMaxSize(maxSize),mIsStop(false) {
			
		}	
		/*void put(const T& e) {
			std::unique_lock<std::mutex> locker(mMutex);
			mNotfull.wait(locker, [this]{return !full();});
			if(mIsStop)return;
			mQueue.push_back(std::forward<T>(e));
			mNotempty.notify_one();
		}*/
		void put(const T& e) {	
			add(e);	
		}
		void put(T&& e) {
			add(std::forward<T>(e));
		}
		void take(T& e){
			std::unique_lock<std::mutex> locker(mMutex);
			mNotempty.wait(locker, [this]{return mIsStop || notempty();});
			if(mIsStop)return;
			e = mQueue.front();
			mQueue.pop_front();
			mNotfull.notify_one();
		}
		bool full() {
			std::unique_lock<std::mutex> locker(mMutex);
			return mQueue.size() >= mMaxSize;
		}
		bool empty() {
			std::unique_lock<std::mutex> locker(mMutex);
			return mQueue.empty();
		}
		size_t size(){
			std::unique_lock<std::mutex> locker(mMutex);
			return mQueue.size();
		}
		void stop(){
			mIsStop = true;
			mNotempty.notify_all();
			mNotfull.notify_all();
		}
private:
	//要注意下面两个方法必须是非同步的，否则死锁
	 bool notfull() {
	 	if(mQueue.size() >= mMaxSize)std::cout << "同步任务队列已满" << std::endl;
		 return mQueue.size() < mMaxSize;
	 }
	 bool notempty() {
		if(mQueue.empty())std::cout << "同步任务队列为空" << std::endl;
		return !mQueue.empty();
	 }
	 template<typename F>
	 void add(F&& e) {
		std::unique_lock<std::mutex> locker(mMutex);
		mNotfull.wait(locker, [this]{return mIsStop || notfull();});
		if(mIsStop)return;
		mQueue.push_back(std::forward<F>(e));
		mNotempty.notify_one();
	 }
private:
	std::list<T> mQueue;
	std::mutex mMutex;
	std::condition_variable mNotempty;
	std::condition_variable mNotfull;
	std::atomic<bool> mIsStop;
	int mMaxSize; //队列最大长度
};
