#include<iostream>
using namespace std;
void qsort(int a[], int be, int end){
	if(be >= end)return;
	else {
		int key = a[be];
		int l = be, h = end - 1;
		while(l < h){
			while(a[h] >= key && h != l)h--;
			a[l] = a[h];
			while(a[l] <= key && h != l)l++;
			a[h] = a[l];
		}
		a[l] = key;
		qsort(a, be, l);
		qsort(a, l+1, end);
	}
}
int main(){
	int a[10];
	int n;
	cin >> n;
	for(int i=0; i < n; i++){
		cin >> a[i];
	}
	qsort(a, 0, n);
	for(int i=0; i < n; i++){
		cout<<a[i]<<" ";
	}
}
