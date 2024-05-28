


async function fetchGetRequest(){
    const url = '/api/v1/';
    var response = await fetch(url, {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    })
    return response.json();
}

async function parseRequest(){
    kursArray = await fetchGetRequest();

    kursArray.forEach(function(row) {
        console.log(row);
    });
}