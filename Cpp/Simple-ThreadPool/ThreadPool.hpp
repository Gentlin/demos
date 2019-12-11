#include<list>
#include<thread>
#include "SyncQueue.hpp"
#include<atomic>
#include<functional>
class ThreadPool {
public:
	using Task = std::function<void()>;
	ThreadPool(const int& i2 = 100):
		mSyncQueue(i2),mIsRunning(false) {
		}
	void start(const int& num = std::thread::hardware_concurrency()) {
		mIsRunning = true;	
		for(int i = 0; i < num; i++){
			mThreadgroup.push_back(std::make_shared<std::thread>(&ThreadPool::excuteTask, this));
		}
	}
	void stop() { 
		mIsRunning = false;
		mSyncQueue.stop();
		for(auto& thread : mThreadgroup) {
			thread->join();
		}
		mThreadgroup.clear();
	}
	void addTask(const Task& task) {
		if(!mIsRunning)return;
		mSyncQueue.put(task);	
	}
	void addTask(Task&& task) {
		if(!mIsRunning)return;
		mSyncQueue.put(std::forward<Task>(task));
	}
private:
	void excuteTask() {
		while(mIsRunning) {
			Task task;
			mSyncQueue.take(task);
			if(!mIsRunning)break;
			task();
		}		
	}
private:
	std::list<std::shared_ptr<std::thread>> mThreadgroup;
	SyncQueue<Task> mSyncQueue;
	std::atomic<bool> mIsRunning;
};
