
dir <- "/home/julesy/workspace/ttp-results/";
file <- "hypervolume_test.csv";

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

write.csv(pivot, file = paste0(dir, substr(file, 1, nchar(file) - 4), "_pivot.csv"))
write.csv(pivot_norm, file = paste0(dir, substr(file, 1, nchar(file) - 4), "_pivot_norm.csv"))

