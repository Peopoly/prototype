var peerId = getParameterByName("peer");

d3.json("/peer/" + peerId, function(peer) {
    d3.select(".user-name")
            .text(peer.name);
    d3.select(".site > a")
            .text(replaceAll(" ", "", peer.name).toLowerCase() + "@.gmail.com");
    d3.select(".about-user")
            .text(peer.description);
});            
            
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
    results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function replaceAll(find, replace, str) {
  return str.replace(new RegExp(find, 'g'), replace);
}