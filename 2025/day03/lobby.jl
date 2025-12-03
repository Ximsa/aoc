content = read("input.txt", String)
#content ="""987654321111111
#811111111111119
#234234234234278
#818181911112111"""

function part_1(bank)
    first_digit, index = findmax(bank[1:end-1])
    second_digit = maximum(bank[index+1:end])
    return first_digit * second_digit
end

function part_2(bank)
    digits = collect("000000000000")
    index = 0
    for i in 1:11
        digits[i], next_index = findmax(bank[index+1:end-12+i])
        index = index+next_index
    end
    digits[12] = maximum(bank[index+1:end])
    return String(digits)
end



result = (content
          |> split
          .|> collect
          .|> part_2
          .|> x -> parse(Int64,x))|> sum
