#include<iostream>
#include<thread>
#include "ThreadPool.hpp"
void test1() {
	ThreadPool pool(5);
	pool.start(4);
	std::thread t1([&pool]{
		for(int i = 0; i < 10; i++) {
			pool.addTask([i]{std::cout <<std::this_thread::get_id()<< ": t1 提交任务 " << i << std::endl;});
		}
	});
	std::thread t2([&pool]{
		for(int i = 0 ;i < 10; i++) {
			pool.addTask([i]{std::cout <<std::this_thread::get_id()<< ": t2 提交任务 "<< i << std::endl;});
		}
	});
	std::this_thread::sleep_for(std::chrono::seconds(2));
	t1.join();
	t2.join();
	pool.stop();
}

int main() {
	test1();
	std::cout << "test1 is over" << std::endl;
}
