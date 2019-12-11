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
	int v;//�ߵ��յ� ����һ�������v�洢��� 
	edge* next; 
};

int main() {
	int t;
	std::cin >> t;
	while(t--) {
		edge ve[100005];//ͼ����ʽ�洢�� �൱�ھ������ʽ�洢 
		for(int i=0; i < 100005; i++) {
		} 
		int n,m,a,b;
		cin >> n >> m;
		//ͼ�Ĺ��� 
		for(int i=0; i < m; i++) {
			cin >> a >> b;//����(a, b) �� 
			edge* p = new edge;
			p->v = b;
			p->next = ve[a].next;
			ve[a].next = p;
			ve[b].v++;
		}
		//topo���� 
		priority_queue<int, vector<int>, greater<int> > topo;//���˵ȼ���ͬҪ���С�� 
		for(int i = 1; i <= n; i++){
			if(ve[i].v == 0) { //���Ϊ0ѹջ 
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
