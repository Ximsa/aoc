content = read("input.txt", String)


demo_content = """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""


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
