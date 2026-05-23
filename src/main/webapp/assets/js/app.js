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

    function showAiLoading() {
        if (document.querySelector('.ai-loading-overlay')) {
            return;
        }

        const overlay = document.createElement('div');
        overlay.className = 'ai-loading-overlay';
        overlay.setAttribute('role', 'status');
        overlay.setAttribute('aria-live', 'polite');
        overlay.style.position = 'fixed';
        overlay.style.inset = '0';
        overlay.style.zIndex = '1000';
        overlay.style.display = 'flex';
        overlay.style.alignItems = 'flex-start';
        overlay.style.justifyContent = 'center';
        overlay.style.paddingTop = 'min(22vh, 180px)';
        overlay.style.background = 'rgba(15, 28, 40, 0.56)';
        overlay.innerHTML = '' +
            '<div class="ai-loading-dialog">' +
            '  <div class="ai-loading-mark" aria-hidden="true">' +
            '    <span></span><span></span><span></span>' +
            '  </div>' +
            '  <div class="ai-loading-copy">' +
            '    <strong>Generating recommendations</strong>' +
            '    <p>The AI assistant is reviewing jobs, profiles, applications, and resume details.</p>' +
            '  </div>' +
            '  <div class="ai-loading-progress" aria-hidden="true"><span></span></div>' +
            '</div>';
        document.body.appendChild(overlay);
        const dialog = overlay.querySelector('.ai-loading-dialog');
        if (dialog) {
            dialog.style.width = 'min(520px, calc(100vw - 40px))';
            dialog.style.padding = '24px 26px 22px';
            dialog.style.borderRadius = '14px';
            dialog.style.background = '#ffffff';
            dialog.style.boxShadow = '0 22px 60px rgba(6, 21, 32, 0.34)';
            dialog.style.textAlign = 'center';
        }
    }

    function initAiLoadingState() {
        const triggers = document.querySelectorAll('[data-ai-loading="true"]');
        triggers.forEach(function (trigger) {
            trigger.addEventListener('click', function (event) {
                event.preventDefault();
                showAiLoading();
                trigger.classList.add('is-loading');
                trigger.setAttribute('aria-busy', 'true');
                if (trigger.tagName === 'BUTTON') {
                    trigger.disabled = true;
                    trigger.dataset.originalText = trigger.textContent;
                    trigger.textContent = 'Generating...';
                    const form = trigger.closest('form');
                    if (form) {
                        if (trigger.name && !form.querySelector('input[name="' + trigger.name + '"]')) {
                            const hidden = document.createElement('input');
                            hidden.type = 'hidden';
                            hidden.name = trigger.name;
                            hidden.value = trigger.value;
                            form.appendChild(hidden);
                        }
                        setTimeout(function () {
                            form.submit();
                        }, 180);
                    }
                    return;
                }

                if (trigger.tagName === 'A' && trigger.href) {
                    setTimeout(function () {
                        window.location.href = trigger.href;
                    }, 180);
                }
            });
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        initRegisterRoleToggle();
        initAiLoadingState();
    });
})();

