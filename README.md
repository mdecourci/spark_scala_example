Processing time(secs) = 442, total tills count=16, total sales count=768000
1.79 approx 2  Sales x2

1 region, 4 shops
Processing time(secs) = 779, total tills count=32, total sales count=1536000
4.4  Salesx2
Processing time(secs) = 3393, total tills count=64, total sales count=3072000
0.8 Salesx2
Processing time(secs) = 2838, total tills count=128, total sales count=6144000
1.8 Salesx1.5
Processing time(secs) = 5272, total tills count=192, total sales count=9216000

1.46444444444444 hr

1104.4921875
461.9140625 secs/Million
572.04861111111111
507.16145833333333
575.52083333333333

approx 570 secs/Million 1/570 Million/sec

8hrs = 8*3600/570 = 50Million

300Million = 47.5hrs = 2 days

Uses
val populateFuture : Future[Unit] = db.run(setupAction.transactionally)
Database.forDataSource(ds, executor = AsyncExecutor("DB Thread", numThreads=50, queueSize=1000))
Processing time(secs) = 2755, total tills count=128, total sales count=6144000

implicit val ec = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())
Database.forDataSource(ds, executor = AsyncExecutor("DB Thread", numThreads=40, queueSize=1000))
Processing time(secs) = 2478, total tills count=128, total sales count=6144000

Database.forDataSource(ds, executor = AsyncExecutor("DB Thread", numThreads=100, queueSize=1000))
Processing time(secs) = 2453, total tills count=128, total sales count=6144000


sqrt(2)                                         //> res0: Double = 1.4142156862745097
sqrt(4)                                   //> res1: Double = 2.0000000929222947
sqrt(1e-6)                                //> res2: Double = 0.031260655525445276
sqrt(0.001)                               //> res3: Double = 0.04124542607499115
sqrt(0.1e-20)                             //> res4: Double = 0.03125
sqrt(1.0e20)                              //> res5: Double = 1.0E10
//	sqrt(1.0e50)
