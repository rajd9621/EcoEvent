/* ============================================
   EcoEvent - Client-side JavaScript
   ============================================ */

document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s ease';
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });

    // Confirm delete actions
    const deleteLinks = document.querySelectorAll('a[onclick*="confirm"]');
    
    // Form validation enhancement
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const requiredFields = form.querySelectorAll('[required]');
            let valid = true;
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    field.classList.add('is-invalid');
                    valid = false;
                } else {
                    field.classList.remove('is-invalid');
                }
            });
            if (!valid) {
                e.preventDefault();
            }
        });
    });

    // Section reorder (drag-drop placeholder)
    const sortableLists = document.querySelectorAll('[data-sortable]');
    sortableLists.forEach(list => {
        let dragging = null;
        list.querySelectorAll('.sortable-item').forEach(item => {
            item.draggable = true;
            item.addEventListener('dragstart', e => { dragging = item; });
            item.addEventListener('dragend', e => { dragging = null; });
            item.addEventListener('dragover', e => {
                e.preventDefault();
                if (dragging && dragging !== item) {
                    const rect = item.getBoundingClientRect();
                    const midY = rect.top + rect.height / 2;
                    if (e.clientY < midY) {
                        list.insertBefore(dragging, item);
                    } else {
                        list.insertBefore(dragging, item.nextSibling);
                    }
                }
            });
        });
    });

    // Color picker preview
    const colorInputs = document.querySelectorAll('input[type="text"].form-control-color, input[name="primaryColor"], input[name="secondaryColor"], input[name="accentColor"]');
    colorInputs.forEach(input => {
        input.addEventListener('input', function() {
            if (this.value && this.value.startsWith('#')) {
                this.style.backgroundColor = this.value;
            }
        });
    });
});

// QR Code scanner placeholder
function scanQRCode() {
    alert('QR scanning feature - implement with camera API');
}

// Copy to clipboard
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        // Show a temporary toast
        const toast = document.createElement('div');
        toast.className = 'position-fixed top-0 end-0 m-3 alert alert-success';
        toast.textContent = 'Copied: ' + text;
        document.body.appendChild(toast);
        setTimeout(() => toast.remove(), 2000);
    });
}
