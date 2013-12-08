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
            .attr("transform", "translate(25,25)");
            
var peerIndexLookup = [];
var peerId = getParameterByName("peer");
console.log("Chain for peer " + peerId);


d3.json("/chain/" + peerId, function(data, error) {
    
    data.peers.forEach(function(element, index) {
        peerIndexLookup[element.id] = index;
    });
    
    var links = [];
    data.connections.forEach(function(c) {
        links.push({
            source: peerIndexLookup[c.producerId],
            target: peerIndexLookup[c.consumerId],
            weight: 1,
        });
    });
    
    force.nodes(data.peers)
         .links(links)
         .start();
         
    var gnodes = svg.selectAll('g.gnode')
                    .data(data.peers)
                    .enter()
                    .append('g')
                    .classed('gnode', true)
                    .on("click", function(d) {
                        console.log("gnode: " + JSON.stringify(d));
                        window.location = "/peer.html?peer=" + d.id;
                     });

    var node = gnodes.append("circle")
                     .attr("class", "node")
                     .attr("r", 7)
                     .call(force.drag);

    var labels = gnodes.append("text")
                       .attr("class", "node-label")
                       .text(function(d) { return d.name; });
                       
    var link = svg.selectAll(".link")
                  .data(links)
                  .enter()
                  .append("line")
                  .attr("class", "link")
                  .style("stroke-width", function(d) { return 2; });
                       
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
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
    results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
