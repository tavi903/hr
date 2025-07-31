<!-- Modal JS -->
function populateForm(elementId, form, row) {
    htmlForm = document.getElementById(elementId);
    htmlToInsert = '';
    if (row != null && row.id != null && form.elements.filter(element => element.name == 'id').length == 0) {
        htmlToInsert+='<input class="w3-input" type="hidden" name="id" value="' + row.id + '">';
    }
    form.elements.forEach(element => {
        if (element.tag == 'label') {
            htmlToInsert+='<label>' + element.innerText + '</label>';
        }
        if (element.tag == 'input') {
            if ((row != null && row[element.name] != null) || element.value != null) {
                if ((row != null && row[element.name] != null))
                    htmlToInsert+='<input class="w3-input ' + ((element.name == 'id' && form.fullFormAction.endsWith('update')) ? 'w3-disabled' : '') + '" type="' + element.type.toLowerCase() + '" name="' + element.name + '" value="' + row[element.name] + '" ' + ((element.name == 'id' && form.fullFormAction.endsWith('update')) ? 'readonly' : '') + '>';
                else
                    htmlToInsert+='<input class="w3-input" type="' + element.type.toLowerCase() + '" name="' + element.name + '" value="' + element.value+'">';
            } else {
                htmlToInsert+='<input class="w3-input" type="' + element.type.toLowerCase() + '" name="' + element.name + '">';
            }
        }
        if (element.tag == 'select') {
            if (row != null) {
                htmlToInsert+='<select class="w3-select" name="' + element.name + '">';
                element.options.forEach(opt => {
                    if (row[element.name] == opt.first) {
                        htmlToInsert+='<option selected value="' + opt.first + '">' + opt.second + '</option>';
                    } else {
                        htmlToInsert+='<option value="' + opt.first + '">' + opt.second + '</option>';
                    }
                });
                htmlToInsert+='</select>';
            } else {
                htmlToInsert+='<select class="w3-select" name="' + element.name + '">';
                element.options.forEach(opt => htmlToInsert+='<option value="' + opt.first + '">' + opt.second + '</option>');
                htmlToInsert+='</select>';
            }
        }
    });
    htmlForm.innerHTML = htmlToInsert + htmlForm.innerHTML;
}
function openModal(event, rowPosition) {
    if (rowPosition == null)
        modalEvent = new CustomEvent('createModal', { bubbles: true, detail: { clientY: event.clientY }});
    else
        modalEvent = new CustomEvent('modifyModal', { bubbles: true, detail: { clientY: event.clientY, numRow: rowPosition }});
    event.target.dispatchEvent(modalEvent);
}
function removeModal(tableId) {
    window[tableId+'Form'].error = null;
    document.getElementById('modal-content').remove();
}
function createModal(tableId, form, pageRefreshed) {
        modal = document.createElement('div');
        modal.id = 'modal-content';
        modal.classList.add('w3-modal');
        modal.style.display = 'block';
        if (isMobileStrict())
            modal.style.paddingTop = (window.innerHeight / 4) + 'px';
        else {
            if (pageRefreshed)
                modal.style.paddingTop = Number(sessionStorage.getItem('scrollPosition')) + (window.innerHeight / 4) + 'px';
            else
                modal.style.paddingTop = window.scrollY + (window.innerHeight / 4) + 'px';
        }
        modalContent = `
                <div class="w3-modal-content">
                    <div class="w3-container w3-blue">
                        <h2 id="modalFormTitle">${form.title}</h2>
                    </div>
        `;
        if (form.error != null && form.error != '') {
            modalContent+=`
                <div id="modal-error" class="w3-panel w3-display-container w3-red">
                        <span onclick="document.getElementById('modal-error').remove()"
                            class="w3-button w3-large w3-red w3-display-topright">X</span>
                    <h3>Error!</h3>
                    <p><pre>${form.error}</pre></p>
                </div>
            `;
        }
        modalContent+= `
            <form id="modalForm" class="w3-container" autocomplete="off">
                <p>
                    <button class="w3-btn w3-round w3-red" onclick="removeModal('${tableId}')">Cancel</button>
                    <input formaction='${form.fullFormAction}' id="${tableId}+'SubmitForm'" class="w3-button w3-round w3-green" type="submit" formmethod="post" formenctype="application/x-www-form-urlencoded" value="Save">
                </p>
            </form>
        </div>
        </div>
        `;
        modal.innerHTML = modalContent;
        document.getElementById('pageContent').appendChild(modal);
}
function isMobileStrict() {
  return window.matchMedia('(pointer: coarse)').matches && window.innerWidth <= 1024;
}

window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('pageContent')
        .addEventListener('modifyModal', event => {
            elementId = event.target.id;
            tableId = elementId.substr(0, elementId.indexOf('ModifyIcon'));
            row = window[tableId][event.detail.numRow];
            form = window[tableId+'Form'];
            form.fullFormAction = form.formAction+'/update';
            createModal(tableId, form);
            populateForm('modalForm', form, row);
        });
    document.getElementById('pageContent')
        .addEventListener('createModal', event => {
            elementId = event.target.id;
            tableId = elementId.substr(0, elementId.indexOf('AddButton'));
            form = window[tableId+'Form'];
            form.fullFormAction = form.formAction+'/create';
            createModal(tableId, form);
            populateForm('modalForm', form, null);
        });
});

