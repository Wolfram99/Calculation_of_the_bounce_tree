async function fetchGetRequest() {
    const url = '/api/v1/';
    var response = await fetch(url, {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    })
    return response.json();
}

async function getResult(object) {
    const url = '/api/v1/';
    var response = await fetch(url, {
        method: 'post',
        body: object
    })
    return response.json();

}

async function parseAmount(object) {
    // var temp = await getResult(object);
    // console.log(temp);

    // var h = document.getElementById("Result");
    // h.value = temp;
    // return temp;

    sum_refusal = await  getResult(object);
    document.getElementById('Result').value = sum_refusal;
}

// Отрисовка таблицы и прас get запроса v0.2
// 3 версия будет -> нужно будет пересмотреть отрисовку иходя из полей объекта который приходит из get запроса
async function parseTable() {
    var tableBody = document.getElementById('dataTable-id');
    kursArray = await fetchGetRequest();
    tableBody.innerHTML = "";

    console.log(kursArray);

    kursArray.forEach(function (row) {
        var newRow = document.createElement('tr');

        newRow.setAttribute("onclick", "dataInLambda(this)");
        newRow.setAttribute("data-bs-dismiss", "modal");
        newRow.setAttribute("aria-label", "Close");

        newRow.setAttribute('class', 'inactiveRow');
        tableBody.appendChild(newRow);

        for (var i = 0; i < Object.keys(row).length; i++) {
            var newCell = document.createElement('td');
            newCell.setAttribute('readonly', 'readonly');
            switch (i) {
                case 0:
                    newCell.textContent = row.id;
                    newCell.setAttribute('hidden', 'hidden');
                    break;
                case 1:
                    newCell.textContent = row.element;
                    break;
                case 2:
                    newCell.textContent = row.lambda;
                    newCell.setAttribute('class', 'lambdaValue');
                    break;
                case 3:
                    newCell.textContent = row.unit;
                    break;
            }
            newRow.appendChild(newCell);

        }
    });

}

function dataInLambda(el){
    document.getElementById('lambda').value = el.querySelector('td.lambdaValue').textContent;
    document.getElementById('lambda').focus();
}

