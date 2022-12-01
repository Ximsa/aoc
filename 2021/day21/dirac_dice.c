#include<stdint.h>
#include<stdio.h>

int_fast8_t rolls [][2] =
  {
    {1,3},
    {3,4},
    {6,5},
    {7,6},
    {6,7},
    {3,8},
    {1,9},
  };

void dice(int_fast8_t pos_1, int_fast8_t score_1, int_fast64_t * win_1,
	  int_fast8_t pos_2, int_fast8_t score_2, int_fast64_t * win_2,
	  int_fast8_t score_mult)
{
  if(score_2 >= 21)
    {
      (*win_1) += score_mult;
    }
  else
    {
      for(size_t i = 0; i < sizeof(rolls) / sizeof(int_fast8_t) / 2; i++)
	{
	  int_fast8_t new_pos = ((pos_1 + rolls[i][1] - 1) % 10)+1;
	  dice(pos_2, score_2, win_2,
	       new_pos,
	       score_1 + new_pos,
	       win_1, score_mult * rolls[i][0]);
	}
    }
}

int main()
{
  int_fast64_t win_1 = 0;
  int_fast64_t win_2 = 0;
  dice(6,0,&win_1,10,0,&win_2,1);
  printf("%li\n%li\n", win_1, win_2);
}
