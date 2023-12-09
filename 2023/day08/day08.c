#include <stdint.h>
#include <stdio.h>

int lookup[2*32*32*32] = {0};

int convert_to_numeric(const char * node)
{
  return 32*32*(node[0]-'A') + 32*(node[1]-'A') + (node[2] - 'A');
}
int is_ending(int node)
{
  return (node & 31) == 'Z' - 'A';
}

int is_beginning(int node)
{
  return (node & 31) == 'A' - 'A';
}

int main(int argc, char * argv[])
{
  FILE * f = fopen(argv[1], "r");
  char line[512] = {0};
  int nodes[16] = {0};
  int nodes_length = 0;
  int instructions[512] = {0};
  int instructions_length = 0;
  uint64_t counter = 0;
  uint64_t counter_increments = 0;
  // parse instructions
  fgets(line, sizeof(line), f);
  for(size_t i = 0; i < sizeof(line) && instructions_length == 0; i++)
    {
      switch(line[i])
	{
	case 'L': instructions[i] = 0; break;
	case 'R': instructions[i] = 1; break;
	default: instructions_length = i; break;
	}
    }
  fgets(line, sizeof(line), f); // ignore next line
  // parse network
  while(fgets(line, sizeof(line),f))
    {
      int index = convert_to_numeric(line);
      int left = convert_to_numeric(line + 7);
      int right = convert_to_numeric(line + 12);
      
      lookup[index*2+0] = left;
      lookup[index*2+1] = right;
      if(is_beginning(index))
	{
	  nodes[nodes_length] = index;
	  nodes_length++;
	}
    }
  int done = 0;
  printf("nodes:%i\n", nodes_length);
  while(!done)
    {
      int instruction = instructions[counter % instructions_length];
      done = 1;
      for(int i = 0; i < nodes_length; i++)
	{
	  nodes[i] = lookup[nodes[i]*2+instruction];
	  if(!is_ending(nodes[i]))
	    {
	      done = 0;
	    }
	}
      counter++;
      if(counter == 1000*1000*1000)
	{
	  counter = 0;
	  counter_increments++;
	  printf("Counter:\t%li*1000000000+%li\n", counter_increments,counter);
	}
    }
  printf("Counter:\t%li*1000000000+%li\n", counter_increments,counter);
  return 0;
}
