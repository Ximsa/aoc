#include <stdio.h>
#include <string.h>

#define WIDTH 131
#define HEIGHT 131
#define QUEUE_SIZE (1024*1024)

char map[HEIGHT][WIDTH] = {0};
int depthmap[HEIGHT][WIDTH] = {0};

typedef struct{
  int x,y,depth;} Point;

struct{
  Point points [QUEUE_SIZE];
  int front;
  int rear;} bfs_queue;

void enqueue(Point p){
  bfs_queue.points[bfs_queue.front] = p;
  bfs_queue.front = (bfs_queue.front + 1) % QUEUE_SIZE;}

Point dequeue(){
  Point ret = bfs_queue.points[bfs_queue.rear];
  bfs_queue.rear = (bfs_queue.rear + 1) % QUEUE_SIZE;
  return ret;}

int empty(){
  return bfs_queue.front==bfs_queue.rear;}

int get_adjacent_tiles(Point position, Point* neighbours){
  int index = 0;
  int x = position.x;
  int y = position.y;
  int depth = position.depth + 1;
  if(x+1 < WIDTH && map[y][x+1] != '#'){
    Point p = {x+1,y,depth};
    neighbours[index] = p;
    index++;}
  if(x-1 >= 0 && map[y][x-1] != '#'){
    Point p = {x-1,y,depth};
    neighbours[index] = p;
    index++;}
  if(y+1 < HEIGHT && map[y+1][x] != '#'){
    Point p = {x,y+1,depth};
    neighbours[index] = p;
    index++;}
  if(y-1 >= 0 && map[y-1][x] != '#'){
    Point p = {x,y-1,depth};
    neighbours[index] = p;
    index++;}
  return index;}

void bfs(Point start){
  enqueue(start);
  while(!empty()){
      Point current = dequeue();
      if(current.depth == 64){
	map[current.y][current.x] = 'O';} // mark reached
      else{
	Point neighbours[4] = {0};
	int num_adjacent = get_adjacent_tiles(current, neighbours);
	for(int i = 0; i < num_adjacent; i++){
	  if(depthmap[neighbours[i].y][neighbours[i].x] != neighbours[i].depth){
	    depthmap[neighbours[i].y][neighbours[i].x] = neighbours[i].depth;
	    enqueue(neighbours[i]);}}}}}

int main(int argc, char * argv[]){
  FILE * f = fopen("input", "r");
  for(int y = 0; fscanf(f, "%s", map[y]) == 1; y++); // read file
  // find start pos
  Point start = {0};
  for(int y = 0; y < HEIGHT; y++)
      for(int x = 0; x < WIDTH; x++)
	if(map[y][x] == 'S'){
	    map[y][x] = '.';
	    start.x = x;
	    start.y = y;}
  bfs(start);
  int positions = 0;
  for(int y = 0; y < HEIGHT; y++){
      for(int x = 0; x < WIDTH; x++){
	  positions += map[y][x] == 'O';
	  printf("%c",map[y][x]);}
      printf("\n");}
  printf("%i\n\n", positions);
  return 0;}
