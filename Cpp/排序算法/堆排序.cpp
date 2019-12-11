#include<iostream>
#include<algorithm>
using namespace std;
//小顶堆 序号从1开始 
void HeapAdj(int a[], int root, int len){
       int child = root * 2;
       while(child <= len){
            if(child+1<=len && a[child]>a[child+1])child++;
            if(a[root] > a[child])swap(a[root],a[child]);
            else break;
            root = child;
            child = child*2;            
       }
} 
void print(int a[], const int& len) {
	 for(int j = 1; j <= len; j++) {
         if(j==1)printf("%d",a[j]);
         else printf(" %d",a[j]);
     }
     printf("\n");
}
void HeapSort(int a[],const int& len){
        for(int i = len/2; i >= 1; i--) {
            HeapAdj(a, i, len);
        }
     
        for(int i = len; i > 1; i--){
            swap(a[1], a[i]);
            HeapAdj(a, 1, i-1);  	
        }
		print(a, len);//结果为降序 
}
int main(){
    int n;
    int a[1000];
    while(scanf("%d",&n)!=EOF && n!=0){
        for(int i=1;i<=n;i++){
            scanf("%d",&a[i]);
        }
        HeapSort(a,n);
    }
}                                 
