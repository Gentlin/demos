#include<iostream>
void print(int a[], const int& len);
void mergeSort(int a[], const int& len) {
	int tmp[len];
	for(int i = 1; i < len; i *= 2) {//���䳤�� 
		int ll = 0; //���������߽� 
		int lr = ll + i;//��������ұ߽� 
		int rl = lr;
		int rr = rl + i >= len ? len : rl + i;
		while(rl < len) {//������������һ���� 
			int p = ll;   //�����ռ�ָ�� 
			int pl = ll; //��������ϲ�����ָ�� 
			int pr = rl; //��������ϲ�����ָ�� 
			while(pl < lr && pr < rr) {
				if(a[pl] < a[pr]) {
					tmp[p] = a[pl];
					pl++;
				} else {
					tmp[p] = a[pr];
					pr++;
				}
				p++;
			}
			while(pl < lr) {
				tmp[p] = a[pl];
				p++;
				pl++;
			} 
			while(pr < rr) {
				tmp[p] = a[pr];
				pr++;
				p++;
			}
			for(p = ll; p < rr; p++) {
				a[p] = tmp[p];
			}
	//		print(a, len);
			ll = rr;
			lr = ll + i;
			rl = lr;
			rr = rl + i >= len ? len : rl + i;
		} 
	} 
}
void print(int a[], const int& len) {
	for(int i = 0; i < len; i++) {
		std::cout << a[i] << " "  ;
	}
	std::cout << std::endl;
}
void test() {
	int a[] = {1, 3, 2, 4};
	mergeSort(a, 4);
	print(a, 4);
}
void test2() {
	int a[] = {1, 4, 100, 3, 4};
	mergeSort(a, 5);
	print(a, 5);
}
void test3() {
	int a[] = {1};
	mergeSort(a, 1);
	print(a, 1);
}
void test4() {
	int a[] = {2, 1};
	mergeSort(a, 2);
	print(a, 2);
}
int main() {
	test();
	test2();
	test3();
	test4(); 
}
