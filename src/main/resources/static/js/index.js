
const endpoint = "http://localhost:8080/";
let repUrl;

function analyseRepository(){
    repUrl = document.getElementById("repository").value;
    var from = document.getElementById("from").value;
    var to = document.getElementById("to").value;

    document.getElementById("fetching").style.display = "block";
    document.getElementById("repo").innerHTML = "Hang on tight. This will take a minute or two...";

    getRepoData(repUrl, from, to);
}

function apiCall(theUrl, callback){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, true ); // true for asynchronous request
    xmlHttp.onreadystatechange = () => {
        if (xmlHttp.status == 200){
            callback(JSON.parse(xmlHttp.responseText));
        } else {
            console.log("Something seems to have gone wrong at " + theUrl);
            document.getElementById("fetching").style.display = "none";
        }
    };
    xmlHttp.send( null );
}

function getRepoData(repUrl, from, to) {
    from = from.replaceAll("-","");
    to = to.replaceAll("-","");

    var theUrl = endpoint + "commit?from=" + from + "&to=" + to + "&repUrl=" + repUrl;
    apiCall(theUrl, processingRepoData);

}

function processingRepoData(json){
    displayRepoData(json);

    var fileDataUrl = endpoint + "fileData?repoDataId=" + json.id;
    apiCall(fileDataUrl, displayDataPerFile);

    var contributorUrl = endpoint + "contributorData?repoDataId=" + json.id;
    apiCall(contributorUrl, displayContributorData);

}

function displayRepoData(json){
    document.getElementById("fetching").style.display = "none";

    document.getElementById("repo").innerHTML = repUrl;
    document.getElementById("noOfCommits").innerHTML = "<h2>" + json.noOfCommits + "</h2>";
    document.getElementById("linesAdded").innerHTML = "<h2>" + json.linesAdded  + "</h2>";
    document.getElementById("linesRemoved").innerHTML = "<h2>" + json.linesRemoved + "</h2>";
    document.getElementById("maxChangeSetSize").innerHTML = "<h2>" + json.maxChangeSetSize + "</h2>";
    document.getElementById("avgChangeSetSize").innerHTML = "<h2>" + json.avgChangeSetSize  + "</h2>";
}

function displayDataPerFile(json){
    let innerHTML = "";

    json.forEach((j) => {
        innerHTML += "<tr>";
        innerHTML += "<td>" + j.fileName + "</td>";
        innerHTML += "<td>" + j.totalCodeChurn + "</td>";
        innerHTML += "<td>" + j.maxCodeChurn + "</td>";
        innerHTML += "<td>" + j.avgCodeChurn + "</td>";
        innerHTML += "<td>" + j.hunkCount + "</td>";
        innerHTML += "<td>" + j.contributorCount + "</td>";
        innerHTML += "<td>" + j.minorContributorCount + "</td>"
        innerHTML += "</tr>";
    });

    document.getElementById("dataPerFileTableBody").innerHTML = innerHTML;
}

function displayContributorData(json){
    let innerHTML = "";

    json.forEach((j) => {
        innerHTML += "<tr>";
        innerHTML += "<td>" + j.contributorName + "</td>";
        innerHTML += "<td>" + j.commitCount + "</td>";
        innerHTML += "</tr>";
    });

    document.getElementById("contributorTableBody").innerHTML = innerHTML;
}
