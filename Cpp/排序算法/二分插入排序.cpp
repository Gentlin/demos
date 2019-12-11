#include<iostream>
using namespace std;

void binsertsort(int a[],int be,int end) {
	for(int i = be+1; i < end; i++) {
		int key = a[i];
		int low = be, high = i-1;
		while(low < high) {
			int m = (low+high) / 2;
			if(a[m] == key) {
				low = m;
				break;
			}
			else if(a[m] > key)high = m-1;
			else low = m+1;
		}
		if(a[low] < key)low++;
		for(int j = i-1; j >= low; j--) {
			a[j+1] = a[j];
		}
		a[low] = key;
	}
}
int main() {
	int a[10];
	int n;
	cin >> n;
	for(int i=0; i < n; i++){
		cin >> a[i];
	}
	binsertsort(a, 0, n);
	for(int i=0; i < n; i++){
		cout << a[i] <<" ";
	}
}
