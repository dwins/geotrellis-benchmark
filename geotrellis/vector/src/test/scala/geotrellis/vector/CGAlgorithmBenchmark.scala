package geotrellis.vector

import scaliper._
import com.vividsolutions.jts.{ geom => jts }
import com.vividsolutions.jts.algorithm.CGAlgorithms.isPointInRing
import com.vividsolutions.jts.densify.Densifier.densify

trait CGAlgorithmSetup { self: Benchmark =>
  override def setUp() = {}
}


class CGAlgorithmBenchmark extends Benchmarks with ConsoleReport with EndpointReport {
  benchmark("CGAlgorithmBenchmarks") {
    run("CGAlgorithm.isInRing(coordinate, coordinate[])") {
      new Benchmark with CGAlgorithmSetup {
        var point: jts.Coordinate = _
        var rings: Vector[Array[jts.Coordinate]] = _
        override def setUp() = {
          val pt = Point(0, 0)
          point = Point(5, 5).jtsGeom.getCoordinate
          val ring = pt.buffer(1).jtsGeom
          rings = (Iterator.from(1)
            .map { i =>
               densify(ring, math.pow(2d, -i))
                 .asInstanceOf[jts.Polygon].getExteriorRing.getCoordinates
            }.take(10)
            .to[Vector])
        }
        def run() = {
          rings.map { r =>
            isPointInRing(point, r)
          }
        }
      }
    }
    run("Polygon.contains(Point)") {
      new Benchmark with CGAlgorithmSetup {
        var point: jts.Point = _
        var rings: Vector[jts.Polygon] = _
        override def setUp() = {
          val pt = Point(0, 0)
          point = Point(0.99, 0.99).jtsGeom
          val ring = pt.buffer(1).jtsGeom
          rings = (Iterator.from(1)
            .map { i =>
              densify(ring, math.pow(2d, -i)).asInstanceOf[jts.Polygon]
            }.take(10)
            .to[Vector])
        }
        def run() = {
          rings.map { r =>
            r.contains(point)
          }
        }
      }

    }
  }
}
