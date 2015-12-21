import scaliper._

trait CGAlgorithmSetup { self: Benchmark =>
  override def setUp() = {}
}


class CGAlgorithmBenchmark extends Benchmarks with ConsoleReport with EndpointReport {
  benchmark("CGAlgorithmBenchmarks") {
    run("Simple benchmark") {
      new Benchmark {
        def run() = println("Ran")
      }
    }
  }
}
