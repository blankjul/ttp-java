
exp <- fromJSON( ,  '~/output/experiment.json')

# create boxplots for all indicators
for(indicator in exp$indicators) {
  print(indicator)
  entry <- lapply(exp$TTP[[indicator]], function(e) fromJSON(,e))
  boxplot(entry, names=exp$algorithms, main=indicator)
}

# collect all the fronts
l = list()
i <- 1
for(problem in exp$problems) {
  for(front in exp[[problem]]$fronts) {
    l[[i]] <- front <- fromJSON( ,  front)
    i <- i + 1
  }
}

# calculate the limits
xlimit <- range(sapply(l, function(entry) sapply(entry, function(x) x[[1]])))
ylimit <- range(sapply(l, function(entry) sapply(entry, function(x) x[[2]])))


# create a vector from a list of points
vector <- function(v, i) {
  sapply(v, function(v) v[[i]])
}

scatter <- function(s, title) {
  # load the pareto front for this specific problem
  front <- fromJSON( ,  s)
  pf <- fromJSON( ,  '~/output/TTP.pf')
  plot(vector(pf, 1), vector(pf, 2), xlim=xlimit, ylim=ylimit, pch=1, xlab = "time", ylab="profit", main=title)
  par(new = TRUE)
  plot(vector(front, 1), vector(front, 2), xlim=xlimit, ylim=ylimit, axes = FALSE, pch=4, xlab = "", ylab = "")
}


for(problem in exp$problems) {
  for(front in exp[[problem]]$fronts) {
    scatter(front, front)
  }
}










