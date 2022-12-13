#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define WIDTH 172
#define HEIGHT 41
#define QUEUE_SIZE (HEIGHT*WIDTH) // 50 is enough, but to be save for a degenerated case....

char heightmap[HEIGHT][WIDTH] = {0};
int visited[HEIGHT][WIDTH] = {0};

typedef struct {
  int x,y,distance;
} Point;

struct {
  Point points [QUEUE_SIZE];
  int front;
  int rear;} bfs_queue;

void enqueue(Point p)
{
  bfs_queue.points[bfs_queue.front] = p;
  bfs_queue.front = (bfs_queue.front + 1) % QUEUE_SIZE;
}
Point dequeue()
{
  Point ret = bfs_queue.points[bfs_queue.rear];
  bfs_queue.rear = (bfs_queue.rear + 1) % QUEUE_SIZE;
  return ret;
}
int empty(){return bfs_queue.front==bfs_queue.rear;}

int compare_heights(int a, int b){
  return b <= 1 + a;} // reverse a and b for part 2

int get_adjacent_tiles(Point position, Point* neighbours){
  int index = 0;
  int x = position.x;
  int y = position.y;
  int distance = position.distance + 1;
  if(x+1 < WIDTH && !visited[y][x+1] && compare_heights(heightmap[y][x+1], heightmap[y][x])){
    Point p = {x+1,y,distance};
    neighbours[index] = p;
    index++;}
  if(x-1 >= 0 && !visited[y][x-1] && compare_heights(heightmap[y][x-1],heightmap[y][x])){
    Point p = {x-1,y,distance};
    neighbours[index] = p;
    index++;}
  if(y+1 < HEIGHT && !visited[y+1][x] && compare_heights(heightmap[y+1][x], heightmap[y][x])){
    Point p = {x,y+1,distance};
    neighbours[index] = p;
    index++;}
  if(y-1 >= 0 && !visited[y-1][x] && compare_heights(heightmap[y-1][x], heightmap[y][x])){
    Point p = {x,y-1,distance};
    neighbours[index] = p;
    index++;}
  return index;}

int found_lowest=0;
void bfs(Point start, Point target)
{
  visited[start.y][start.x] = 1;
  enqueue(start);
  while(!empty()){
    Point current = dequeue();
    if(current.x == target.x && current.y == target.y)
      printf("found target, distance: %i\n", current.distance);
    if(!found_lowest && heightmap[current.y][current.x] == 'a'){
      found_lowest = 1;
      printf("found lowest point, distance: %i\n", current.distance);}
    Point neighbours[4] = {0};
    int length = get_adjacent_tiles(current, neighbours);
    for(int i = 0; i < length; i++){
      visited[neighbours[i].y][neighbours[i].x] = 1;
      enqueue(neighbours[i]);}}}

int main(){
  FILE * f = fopen("input", "r");
  char line[WIDTH + 1] = {0};
  for(int i = 0; fscanf(f, "%s", line) == 1; i++)
    memcpy(heightmap[i], line, WIDTH); // read file line by line
  // find start and target position
  Point start = {0};
  Point target = {0};
  for(int y = 0; y < HEIGHT; y++)
    for(int x = 0; x < WIDTH; x++){
      if(heightmap[y][x] == 'S'){
	heightmap[y][x] = 'a';
	start.x = x;
	start.y = y;
      }
      if(heightmap[y][x] == 'E'){
	heightmap[y][x] = 'z';
	target.x = x;
	target.y = y;}}
  // perform bfs search
  bfs(target, start);
}
