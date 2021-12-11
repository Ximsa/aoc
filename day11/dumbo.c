#include <stdio.h>
#include <string.h>

#define WIDTH 10
#define HEIGHT 10
#define ITERATE_W_H(fun) for(int y = 0; y < HEIGHT; y++) for(int x = 0; x < WIDTH; x++) fun

int flashed[HEIGHT][WIDTH] = {0};
char dumbos[HEIGHT][WIDTH] = {0};
int flash_counter = 0;

void tick(size_t x, size_t y){
  if (!(x < 0 || x >= WIDTH ||
        y < 0 || y >= HEIGHT ||
        flashed[y][x])){ // within bounds and not flashed yet?
    dumbos[y][x]++; // increment
    if(dumbos[y][x] > '9'){ // need to flash?
      flash_counter++;
      flashed[y][x] = 1;
      dumbos[y][x] = '0';
      tick(x-1,y-1); // recursive call to neighbour cells
      tick(x-1,y+0);
      tick(x-1,y+1);
      tick(x+0,y-1);
      tick(x+0,y+1);
      tick(x+1,y-1);
      tick(x+1,y+0);
      tick(x+1,y+1);}}}

int main(){
  FILE * f = fopen("input", "r");
  char line[WIDTH + 1] = {0};
  for(size_t i = 0; fscanf(f, "%s", line) == 1; i++)
    memcpy(dumbos[i], line, WIDTH); // read file line by line
  int runs = 0;
  char sync = 0;
  while(!sync){
    runs++; sync = 1;
    ITERATE_W_H(flashed[y][x] = 0); // reset flashed
    ITERATE_W_H(tick(x,y)); // increment and flash
    ITERATE_W_H(sync &= dumbos[y][x] == dumbos[0][0]);}// check if dumbos are in sync
  printf("flash counter:\t%i\nrun counter:\t%i\n", flash_counter, runs);}
