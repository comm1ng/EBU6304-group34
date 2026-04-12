(function () {
    function initRegisterRoleToggle() {
        const roleSelect = document.querySelector('[data-toggle-workunit="true"]');
        const workUnitBlock = document.getElementById('workUnitBlock');
        const workUnitInput = document.getElementById('workUnit');

        if (!roleSelect || !workUnitBlock || !workUnitInput) {
            return;
        }

        const updateVisibility = function () {
            const isMo = roleSelect.value === 'MO';
            workUnitBlock.style.display = isMo ? 'block' : 'none';
            workUnitInput.required = isMo;
            if (!isMo) {
                workUnitInput.value = '';
            }
        };

        roleSelect.addEventListener('change', updateVisibility);
        updateVisibility();
    }

    document.addEventListener('DOMContentLoaded', function () {
        initRegisterRoleToggle();
    });
})();

