package nextstep.subway.map.application;

import java.util.ArrayList;
import java.util.List;
import nextstep.subway.line.domain.Line;
import nextstep.subway.map.domain.SectionEdge;
import nextstep.subway.map.domain.SubwayGraph;
import nextstep.subway.map.domain.SubwayPath;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.springframework.stereotype.Service;

@Service
public class PathService {

    public SubwayPath findPath(List<Line> lines, Station source, Station target) {
        SubwayGraph graph = new SubwayGraph(SectionEdge.class);
        graph.addVertexWith(lines);
        graph.addEdge(lines);

        // 다익스트라 최단 경로 찾기
        DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath =
            new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionEdge> path = dijkstraShortestPath.getPath(source, target);

        return convertSubwayPath(path);
    }

    private SubwayPath convertSubwayPath(GraphPath<Station, SectionEdge> graphPath) {
        return new SubwayPath(edges(graphPath), stations(graphPath));
    }

    private List<Station> stations(GraphPath<Station, SectionEdge> graphPath) {
        return graphPath.getVertexList();
    }

    private List<SectionEdge> edges(GraphPath<Station, SectionEdge> graphPath) {
        return new ArrayList<>(graphPath.getEdgeList());
    }
}
