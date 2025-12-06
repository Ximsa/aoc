
content ="""123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """
content = read("input.txt", String)
_, tmp... = (content
             |> x -> split(x,'\n')
             |> reverse
             .|> split
             .|> reverse
             .|> x -> String.(x))
puzzle = stack(tmp)

function part_1(puzzle)
    result = 0
    for row in eachrow(puzzle)
        operator, numbers... = row
        operator = operator == "*" ? (*) : (+)
        result += numbers |> x -> parse.(Int64,x) |> x -> reduce(operator,x)
    end
    return result
end

result_1 = part_1(puzzle)

# part2
content = readlines("input.txt") |> stack |> x -> reverse(x,dims=1)
function part_2(content)
    result = 0
    numbers = []
    for row in eachrow(content)
        number..., operant = row
        number = number |> String
        if number |> strip |> isempty |> (!)
            number |> x -> parse(Int64, x) |> x -> push!(numbers, x)
        end
        if operant == '*'
            result += numbers |> prod
            numbers = []
        end
        if operant == '+'
            result += numbers |> sum
            numbers = []
        end
    end
    return result
end

part_2(content)


   
    
    
