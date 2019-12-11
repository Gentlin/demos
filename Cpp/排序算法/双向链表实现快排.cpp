#include<iostream>
class List {
private:
	struct Node {
		int data;
		Node* prev;
		Node* next;
	};
	Node* pHead;
	Node* pEnd;
public:
	List(int a[], const int& len) {
		if(len == 0)return;
		pHead = new Node;
		pHead->data = a[0];
		pHead->prev = pHead->next = NULL;
		Node* p = pHead;
		for(int i = 1; i < len; i++) {
			Node* n = new Node;
			n->data = a[i];
			n->next = NULL;
			n->prev = p;
			p->next = n;
			p = p->next;
		}
		pEnd = p;
	}
	void sort() {
		qsort(pHead, pEnd);
	}
	void qsort(Node* begin, Node* end) {
		if(begin == end)return;
		Node* low = partion(begin, end);
		if(low != begin)qsort(begin, low->prev);
		if(low != end)qsort(low->next, end);
	}
	Node* partion(Node* &begin, Node* &end) {
	 
		Node* low = begin;
		Node* high = end;
		while(low != high && high->data >= low->data) {
			high = high->prev;
		}
		swap(low, high);
		begin = low; //确保begin 和 end 的逻辑意义正确 
		if(low == end)end = high;
		while(low != high && high->data >= low->data) {
			low = low->next;
		}
		swap(low, high);
		
		while(low != high) {
			while(low != high && high->data >= low->data) {
				high = high->prev;
			}
			swap(low, high);
			while(low != high && high->data >= low->data) {
				low = low->next;
			}
			swap(low, high);
		}
	//	print();
		return low;
	}
	void print() {
		Node* p = pHead;
		while(NULL != p) {
			std::cout << p->data << " ";
			p = p->next;
		}
		std::cout << std::endl;
	}
private:
	void swap(Node* &a, Node* &b) {
	  //  std::swap(a->data, b->data); // 如果允许修改数据域，用这种方法 
	//	return;
	//	print();
		if(a->prev == b) {
			if(NULL != b->prev)b->prev->next = a;
			if(NULL != a->next)a->next->prev = b;
			b->next = a->next;
			a->prev = b->prev;
			a->next = b;
			b->prev = a;
			
		} else if(a->next == b) {
			if(NULL != a->prev)a->prev->next = b;
			if(NULL != b->next)b->next->prev = a;
			a->next = b->next;
			b->prev = a->prev;
			a->prev = b;
			b->next = a;
		} else {//a b 不相邻的情况 
			if(NULL != a->prev)a->prev->next = b;
			if(NULL != a->next)a->next->prev = b;
			if(NULL != b->prev)b->prev->next = a;
			if(NULL != b->next)b->next->prev = a;	
			std::swap(a->prev, b->prev);
			std::swap(a->next, b->next);
		}	
		if(a == pHead)pHead = b;
		else if(b == pHead)pHead = a;
		if(a == pEnd)pEnd = b;
		else if(b == pEnd)pEnd = a;
		std::swap(a, b);
	//	print();
	}

};
void test1() {
	int a[] = {5, 3, 100, 9, 1, 1,3};
	List b(a, 7);
	b.sort();
	b.print();
} 
void test2() {
	int a[] = {5, 3, 100, 9,  1,3};
	List b(a, 6);
	b.sort();
	b.print();
}
void test3() {
	int a[] = {5};
	List b(a, 1);
	b.sort();
	b.print();
}
void test4() {
	int a[] = {6, 5};
	List b(a, 2);
	b.sort();
	b.print();
}
int main() {
	test1();
	test2();
	test3();
	test4();
}
