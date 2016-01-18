
dir <- "/home/julesy/workspace/ttp-results/";
file <- "singleobjective.csv";

csv <- read.csv(paste0(dir, file),  sep = ",")
csv$result <- round(csv$result, 4)
csv$problem <- factor(csv$problem, levels(csv$problem)[unique(csv$problem)] )

df_min <- aggregate(csv$result, by=list(csv$problem), FUN=min)
colnames(df_min) <- c("problem","value")

df_max <- aggregate(csv$result, by=list(csv$problem), FUN=max)
colnames(df_max) <- c("problem","value")

csv$min <- apply(csv, 1, function(x) df_min[df_min$problem==x[1],]$value)
csv$max <- apply(csv, 1, function(x) df_max[df_max$problem==x[1],]$value)


csv$norm <- (csv$result - csv$min) / (csv$max - csv$min)


library(reshape)
pivot <- cast(csv, algorithm ~ problem, fun.aggregate=median, value="result")
pivot_norm <- cast(csv, algorithm ~ problem, fun.aggregate=median, value="norm")

library(ggplot2)
agg_norm <- aggregate(csv$norm, by=list(csv$problem, csv$algorithm), FUN=median)
colnames(agg_norm) <- c("problem","algorithm", "norm")

p <- ggplot(agg_norm, aes(x=problem, y=norm, shape=algorithm, color=algorithm)) + geom_point(size=3)
p <- p + theme(axis.text.x = element_text(angle = 90, hjust = 1)) + scale_shape_manual(values=c(15,16,17,18,4,23,6,7,8))
p <- p + xlab("problem") + ylab("normalized values")
print(p)

write.csv(pivot, file = paste0(dir, substr(file, 1, nchar(file) - 4), "_pivot.csv"))
write.csv(pivot_norm, file = paste0(dir, substr(file, 1, nchar(file) - 4), "_pivot_norm.csv"))

