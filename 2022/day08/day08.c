#include <stdio.h>
#include <string.h>

#define WIDTH 99
#define HEIGHT 99

char trees[HEIGHT][WIDTH] = {0};
int visible[HEIGHT][WIDTH] = {0};
int scores[HEIGHT][WIDTH] = {0};

int main(){
  FILE * f = fopen("input", "r");
  char line[WIDTH + 1] = {0};
  for(int i = 0; fscanf(f, "%s", line) == 1; i++)
    memcpy(trees[i], line, WIDTH); // read file line by line
  for(int y = 0; y < HEIGHT; y++) // initialize score matrix
    for(int x = 0; x < WIDTH; x++)
      scores[y][x] = 1;
  for(int y = 0; y < HEIGHT; y++){ // horizontal
    char treshold = 0; // left-right    
    for(int x = 0; x < WIDTH; x++){
      if(trees[y][x] > treshold){ // part 1
	treshold = trees[y][x];
	visible[y][x] = 1;}
      int score = 0; // part 2
      int offset = x-1;
      while(offset >= 0){
	score++;
	if(trees[y][x] <= trees[y][offset]) break;
	offset--;}
      scores[y][x] *= score;}
    treshold = -1; // right-left
    for(int x = WIDTH - 1; x >= 0; x--){
      if(trees[y][x] > treshold){ // part 1
	treshold = trees[y][x];
	visible[y][x] = 1;}
      int score = 0; // part 2
      int offset = x+1;
      while(offset < WIDTH){
	score++;
	if(trees[y][x] <= trees[y][offset]) break;
	offset++;}
      scores[y][x] *= score;}}
  for(int x = 0; x < WIDTH; x++){ // vertical
    char treshold = -1; // up-down
    for(int y = 0; y < HEIGHT; y++){
      if(trees[y][x] > treshold){
	treshold = trees[y][x];
	visible[y][x] = 1;}
      int score = 0; // part 2
      int offset = y-1;
      while(offset >= 0){
	score++;
	if(trees[y][x] <= trees[offset][x]) break;
	offset--;}
      scores[y][x] *= score;}
    treshold = -1; // down-up
    for(int y = HEIGHT - 1; y >= 0; y--){
      if(trees[y][x] > treshold){
	treshold = trees[y][x];
	visible[y][x] = 1;}
      int score = 0; // part 2
      int offset = y+1;
      while(offset < HEIGHT){
	score++;
	if(trees[y][x] <= trees[offset][x]) break;
	offset++;}
      scores[y][x] *= score;}}
  // count visible trees and highscore
  int count = 0;
  int max = 0;
  for(int y = 0; y < HEIGHT; y++)
    for(int x = 0; x < WIDTH; x++){
      count += visible[y][x];
      if(scores[y][x] > max) max = scores[y][x];}
  printf("%i\t%i\n", count, max);}
