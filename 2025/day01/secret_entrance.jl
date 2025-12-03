content = readlines(open("input.txt"))


demo_content = split("""L68
L30
R48
L5
R60
L55
L1
L99
R14
L82""","\n")


function parse_line(line)
    x = line[1]
    xs = line[2:end]
    return parse(Int64, xs) * (x == 'R' ? 1 : -1)
end

function parse_line2(line)
    x = line[1]
    xs = line[2:end]
    repeats = parse(Int64, xs)
    number = x == 'R' ? 1 : -1
    return fill(number, repeats)
end

parsed = collect(Iterators.flatten(map(parse_line2, content)))


result = 50
counter = 0
for number in parsed
    global result += number
    if result % 100 == 0
        global counter+=1
    end
end
println(counter)
   
