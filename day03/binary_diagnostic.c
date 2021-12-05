#include <stdio.h>
#define LINE_LENGTH 12
int * inverse(int length, int * bin)
{
  while(length--)
      bin[length] = !bin[length];
  return bin;
}

int binary_to_dec(int length, const int * bin)
{
  int result = 0;
  int power = 0;
  while(length--)
      result |= bin[length] << power++;
  return result;
}

int main()
{
  FILE * f = fopen("input", "r");
  char line[LINE_LENGTH] = {0};
  int count[LINE_LENGTH + 1] = {0};
  for(; fscanf(f, "%12s", line) == 1; count[LINE_LENGTH]++)
    for(int m = 0; m < LINE_LENGTH; m++)
      count[m] += line[m] - '0';
  printf("%i\n", count[LINE_LENGTH]);
  for(int i = 0; i < LINE_LENGTH; i++)
    count[i] = count[i] < count[LINE_LENGTH] / 2;
  printf("%i\n", binary_to_dec(LINE_LENGTH, count) * binary_to_dec(LINE_LENGTH, inverse(LINE_LENGTH, count)));
}
