#include<iostream>
using namespace std;	

void mergesort(int a[], int be, int end){
   
	 if(end <= be + 1)return;
	 else {
	    static int b[100];//¸¨Öú¿Õ¼ä
	 	int m = (be + end) / 2;
	 	mergesort(a, be, m);
	 	mergesort(a, m , end);	
		int i = be, j = m, k = be;
		while(i<m && j<end) {
			if(a[i] < a[j]) {
				b[k++] = a[i++];
			} else {
				b[k++] = a[j++];
			}
		}
		while(i < m) {
			b[k++] = a[i++];
		}
		while(j < end) {
			b[k++] = a[j++];
		}
	//	cout<<be<<"-"<<end<<endl;
	 	for(k=be; k < end; k++){
	 		a[k] = b[k];
	 		//cout<<a[k]<<" ";
		 }
		 //cout<<endl;
	 }
}
int main() {
	int a[10];
	int n;
	cin >> n;
	for(int i=0; i < n; i++){
		cin >> a[i];
	}
	mergesort(a, 0, n);
	for(int i=0; i < n; i++){
		cout << a[i] <<" ";
	}
}
