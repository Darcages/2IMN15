/**
 * Returns true if the provided value is an integer, otherwise false is returned.
 * @param value The value that is to be checked.
 * @returns {boolean} True if the value is an integer. Otherwise false.
 */
function isInt(value) {
    return !isNaN(value) &&
        parseInt(Number(value)) == value &&
        !isNaN(parseInt(value, 10));
}

/**
 * Sets the error message of element with identifier 'error-message' on the page and removes its hidden property.
 * @param message The error message that is to be displayed.
 */
function setErrorMessage(message) {
    var field = $('#error-message');
    var group = field.parent('.form-group');

    if (field.length == 1) {
        field.text(message);
        group.removeAttr('style');
    } else {
        console.log("Error field not found. Message: " + message);
    }
}

/**
 * Disables the error message of the element with identifier 'error-message' by hiding it.
 */
function disableErrorMessage() {
    var field = $('#error-message');
    var group = field.parent('.form-group');

    if (field.length == 1) {
        field.text('');
        group.css({ display: 'none' });
    }
}

/**
 * Creates a table row from the provided array.
 * @param id The identifier that is saved as data with the table row. This data has the key 'id'.
 * @param array The array for which each element corresponds with a column.
 * @returns {*|jQuery} The created table row.
 */
function toTableRow(id, array) {
    var row = $('<tr>')
        .data('id', id);

    for (var i = 0; i < array.length; i++) {
        var cell = $('<td>');
        cell.text(array[i]);

        row.append(cell);
    }

    return row;
}

/**
 * Creates a table row from the provided array along with an additional column to remove it.
 * @param id The identifier that is saved as data with the table row. This data has the key 'id'.
 * @param array The array for which each element corresponds with a column.
 * @param remove The function that is called once the row is tried to be removed. The stored identifier along with the
 *        JQuery row element is provided as parameter.
 * @returns {*|jQuery} The created table row.
 */
function toTableRowRemovable(id, array, remove) {
    var row = toTableRow(id, array);
    var cell = $('<td>');

    var btn = $('<button>')
        .attr('type', 'button')
        .attr('title', 'Delete')
        .addClass('close')
        .html('&times;')
        .click(function() {
            var selectedRow = $(this)
                .parent('td')
                .parent('tr');

            remove(selectedRow.data('id'), selectedRow);
        });

    cell.append(btn);
    row.append(cell);
    return row;
}