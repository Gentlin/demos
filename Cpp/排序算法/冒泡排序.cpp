#include<iostream> 
using namespace std;
void bubsort(int a[],int be,int end){
	for(int i = end; i >= 0;i--){
		for(int j = 0; j <= i; j++){
			if(a[j] > a[j+1]){
				swap(a[j], a[j+1]);
			}
		}
	}
} 
int main(){
	int a[10];
	for(int i=0;i<10;i++){
		cin >> a[i];
	}
	bubsort(a,0,9);
	for(int i=0;i<10;i++){
		cout << a[i] << " ";
	}
}
