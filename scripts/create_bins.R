
file <- "/home/julesy/bins.csv";
csv <- read.csv(file,  sep = ",")


mp <- barplot(csv$obj, main="Distribution of Boolean Picking PLans",xlab="make",las=2)
axis(1,at=mp,labels=csv$bin)


# Generate data
library(ggplot2)

p <- ggplot(csv, aes(csv$id, csv$obj)) + 
  geom_point() + 
  scale_x_discrete(labels=csv$bin)  + 
  theme(axis.text.x = element_text(angle = 90, hjust = 1))

print(p)



c <- ggplot(csv, aes(csv$id, csv$obj)) + geom_bar()
print(c)

qplot(color, data=diamonds, geom="bar")