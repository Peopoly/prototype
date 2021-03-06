var width = 600;
var height = 600;

var force = d3.layout
              .force()
              .charge(-1 * (height / 3))
              .linkDistance(height / 10)
              .size([width, height]);

var svg = d3.select(".circle-profile")
            .append("svg")
            .attr("width", width)
            .attr("height", height)
            ;
            
var link;
var peerIndexLookup = [];

d3.json("/peers", function(peers, error) {
    
    peers.forEach(function(element, index) {
        peerIndexLookup[element.id] = index;
    });
    
    force.nodes(peers)
         .start();
         
    var gnodes = svg.selectAll('g.gnode')
                    .data(peers)
                    .enter()
                    .append('g')
                    .classed('gnode', true)
                    .on("click", function(d) {
                        console.log("gnode: " + JSON.stringify(d));
                        window.location = "/details.html?peer=" + d.id;
                     });

    var node = gnodes.append("circle")
                     .attr("class", "node")
                     .attr("r", 7)
                     .call(force.drag);

    var labels = gnodes.append("text")
                       .attr("class", "node-label")
                       .text(function(d) { return d.name; });
                       
    node.append("title")
        .text(function(d) { return d.name; });
         
    force.on("tick", function() {
            
        if (link) {
            link.attr("x1", function(d) { return d.source.x; })
                .attr("y1", function(d) { return d.source.y; })
                .attr("x2", function(d) { return d.target.x; })
                .attr("y2", function(d) { return d.target.y; });
        }
        
        gnodes.attr("transform", function(d) { 
            return 'translate(' + [d.x, d.y] + ')'; 
        });
    });
    
    d3.json("/connections", function(connections, error) {
    
        var links = [];
        connections.forEach(function(c) {
            links.push({
                source: peerIndexLookup[c.producerId],
                target: peerIndexLookup[c.consumerId],
                weight: 1,
            });
        });
        force.links(links)
             .start();

        link = svg.selectAll(".link")
                      .data(links)
                      .enter()
                      .append("line")
                      .attr("class", "link")
                      .style("stroke-width", function(d) { return 2; })
//                                  .on("mouseover", function(d) {
//                                        console.log(JSON.stringify(d));
//                                   })
                       ;

    });
});
