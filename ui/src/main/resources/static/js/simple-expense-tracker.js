//doughnut
var ctxCategoricalExpenses = document.getElementById("categoricalExpenses").getContext('2d');
var categoricalExpensesLineChart = new Chart(ctxCategoricalExpenses, {
    type: 'doughnut',
    data: {
        labels: ["Food", "Transportation", "Entertainment", "Shelter"],
        datasets: [{
            data: [ [[${aggregates.get("Food")}]],
                [[${aggregates.get("Transport")}]],
                [[${aggregates.get("Entertainment")}]],
                [[${aggregates.get("Shelter")}]] ],
            backgroundColor: ["#F7464A", "#46BFBD", "#FDB45C", "#949FB1"],
            hoverBackgroundColor: ["#FF5A5E", "#5AD3D1", "#FFC870", "#A8B3C5"]
        }]
    },
    options: {
        responsive: true
    }
});
