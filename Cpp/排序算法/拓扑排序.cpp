#include<iostream>
#include<vector>
#include<algorithm>
#include<queue>
using namespace std;
/*
sample input 
sample output 
*/ 
struct edge {
	int v;//边的终点 ，第一个顶点的v存储入度 
	edge* next; 
};

int main() {
	int t;
	std::cin >> t;
	while(t--) {
		edge ve[100005];//图的链式存储， 相当于矩阵的链式存储 
		for(int i=0; i < 100005; i++) {
		} 
		int n,m,a,b;
		cin >> n >> m;
		//图的构建 
		for(int i=0; i < m; i++) {
			cin >> a >> b;//存在(a, b) 边 
			edge* p = new edge;
			p->v = b;
			p->next = ve[a].next;
			ve[a].next = p;
			ve[b].v++;
		}
		//topo排序 
		priority_queue<int, vector<int>, greater<int> > topo;//拓扑等级相同要输出小的 
		for(int i = 1; i <= n; i++){
			if(ve[i].v == 0) { //入度为0压栈 
				topo.push(i);
			}
		}
		while(!topo.empty()) {	
			int j = topo.top();
			cout << j << endl; 
			topo.pop();
			edge* p = ve[j].next;
			while(p != NULL) {
				ve[p->v].v--;
				if(ve[p->v].v == 0){
					topo.push(p->v);
				}
				p = p->next;
			}
		}
	}
}
