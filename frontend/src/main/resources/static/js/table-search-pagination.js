function searchWithPagination(event, element) {
    tableId = element.getAttribute('data-tableId');
    if (element.tagName == 'A')
        customEvent = new CustomEvent('searchWithPagination', { bubbles: true, detail: { page: element.getAttribute('data-page'), tableId: tableId }});
    else {
        searchForm = document.getElementById(tableId+'SearchForm');
        searchDto = {};
        searchForm.childNodes.forEach(element => {
            if (element.name != null) {
                searchDto[element.name] = element.value;
            }
        });
        customEvent = new CustomEvent('searchWithPagination', { bubbles: true, detail: { page: 0, tableId: tableId, searchDto: JSON.stringify(searchDto)}});
    }
    event.target.dispatchEvent(customEvent);
}
function manageSearchEvent(event) {
    body = [{"name": event.detail.tableId+'.page', "value": event.detail.page}];
    if (event.detail.searchDto != null)
        body.push({"name": event.detail.tableId+'.searchDto', "value": event.detail.searchDto});
    fetch("/session", { "method": "POST", "body": JSON.stringify(body), headers: {"Content-type": "application/json; charset=UTF-8"}})
        .then(response => { if (response.status == 200) {location.reload();} });
}
window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('pageContent')
        .addEventListener('searchWithPagination', manageSearchEvent);
});

function downloadCsv(element) {
    tableId = element.getAttribute('data-tableId');
    searchForm = document.getElementById(tableId+'SearchForm');
    searchDto = {};
    searchForm.childNodes.forEach(element => {
        if (element.name != null) {
            searchDto[element.name] = element.value;
        }
    });
    body = [{"name": tableId+'.searchDto', "value": JSON.stringify(searchDto)}];
    status = null;
    fetch("/session", { "method": "POST", "body": JSON.stringify(body), headers: {"Content-type": "application/json; charset=UTF-8"}}).then(
        response => {
            if (response.status == 200) {
                fetch("/employee/download-csv", { "method": "GET", headers: {"Content-type": "application/json; charset=UTF-8"}});
            }
        }
    );
}
