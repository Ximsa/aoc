library(pheatmap)

content <- read.table(text = gsub("->", ",", readLines("input")), sep=",", col.names = c("x1","y1","x2","y2"))

field <- matrix(0
               ,nrow=max(content[c(c("y1","y2"))])+1
               ,ncol=max(content[c(c("x1","x2"))])+1)

draw_lines <- function(row){
    ##if(row["x1"] == row["x2"] || row["y1"] == row["y2"]) ## uncomment for straights only
    {
        x1 <- row["x1"] + 1
        x2 <- row["x2"] + 1
        y1 <- row["y1"] + 1
        y2 <- row["y2"] + 1
        mapply(function(i,j) field[j, i] <<- field[j, i] + 1
              ,x1:x2
              ,y1:y2)}}

invisible(apply(content, 1, draw_lines))

print(length(field[field >= 2]))

pheatmap(field, cluster_rows = FALSE, cluster_cols = FALSE, filename="hydrothermal_venture.pdf")
