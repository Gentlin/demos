#include<iostream>

void selectSort(int a[], const int& n) {
	for(int i = 0; i < n-1; i++) {
		int mini=i,min=a[i];
		for(int j = i+1; j < n; j++) {
			if(a[j] < min){
				mini = j;
				min = a[j];
			}
		}
		std::swap(a[i], a[mini]);
	}
} 

int main(){
	int a[1000];
	int t,n;
	std::cin >> t;
	while(t--) {
		std::cin >> n;
		for(int i = 0; i < n; i++)std::cin >> a[i];
		selectSort(a, n);
		for(int j = 0; j < n; j++)std::cout << a[j] <<" ";
		std::cout << std::endl; 
	}
}
