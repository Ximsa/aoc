content = read("input.txt", String)
demo_content ="""987654321111111
811111111111119
234234234234278
818181911112111"""




id_ranges_raw = split(content, ",")
ids = collect(Iterators.flatten(map(function(x)
                                        lower, upper = split(x,'-')
                                        return collect(range(parse(Int64,lower),parse(Int64,upper)))
                                    end, id_ranges_raw)))


function is_invalid(id)
    id_str = repr(id)
    splitpoint = div(length(id_str),2)
    return length(id_str) % 2 == 0 && id_str[1:splitpoint] == id_str[splitpoint+1:end]
end

result = sum(filter(is_invalid, ids))
