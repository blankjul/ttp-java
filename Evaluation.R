library(rjson)

#setwd("/home/julesy/Workspace/ttp-java")

plots <- list()
dir <- "experiment/"
exp <- fromJSON(,paste(dir, "experiment.json", sep=""))

limits <- list()

# collect all the fronts
for(p in exp$problems) {
  l = list()
  i <- 1
  for(a in exp$algorithms) {
    for(e in exp$runs) {
      file <- paste(dir, p, "_",a,"_", e, ".pf", sep = "")
      print(file);
      front <- fromJSON(,file)
      l[[p]][[i]] <- front
      i <- i + 1
    }
  }
  # calculate the limits
  limits[[p]]$xlimit <- range(sapply(l, function(entry) sapply(entry, function(x) x[[1]])))
  limits[[p]]$ylimit <- range(sapply(l, function(entry) sapply(entry, function(x) x[[2]])))
}


#tmp = sapply(l[['ttp-20-10']], function(x) x[[2]]);

last <- function(x) { return( x[length(x)] ) }

scatter <- function(p, a, e, xlimit, ylimit) {
  
  path_front <- paste(dir, p, "_",a,"_", e, ".pf", sep = "")
  print(path_front)
  front <- fromJSON( ,   path_front)
  
  ref <- fromJSON( ,  paste(dir, p, ".pf", sep = ""))
  svg(paste(dir, p, "_", a ,"_", e, ".svg", sep = ""))
  plot(ref[[1]], ref[[2]], xlim=xlimit, ylim=ylimit, pch=1, xlab = "time", ylab="profit", main=paste(p, "_",a,"_", e, sep = ""))
  par(new = TRUE)
  plot(front[[1]], front[[2]], xlim=xlimit, ylim=ylimit, axes = FALSE, pch=4, xlab = "", ylab = "")
  dev.off()
}


for(p in exp$problems) {
  for(a in exp$algorithms) {
    for(e in exp$runs) {
      scatter(p, a, e, limits[[p]]$xlimit, limits[[p]]$ylimit)
    }
  }
}

for(indicator in exp$indicators) {
  for(p in exp$problems) {
    entry <- lapply(exp$algorithms, function(a) fromJSON(,paste( dir, p, "_",a, ".", indicator, sep = "")))
    svg(paste(dir, p, "_", indicator, ".svg", sep = ""))
    op <- par(mar = c(5, 10, 4, 2) + 0.1)
    boxplot(entry, names=exp$algorithms, main=paste(p, indicator, sep = "-"), horizontal = TRUE, las = 1, cex.axis = 0.7)
    par(op)
    dev.off()
  }
}










