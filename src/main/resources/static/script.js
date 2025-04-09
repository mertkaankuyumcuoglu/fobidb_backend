async function fetchTeachers() {
    try {
        const response = await fetch('/api/v1/teacher');
        if (!response.ok) {
            throw new Error('Fehler beim Laden der Daten');
        }
        const teachers = await response.json();

        const tableBody = document.getElementById('teacherTableBody');
        tableBody.innerHTML = ""; // Tabelle leeren

        teachers.forEach(teacher => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${teacher.id}</td>
                <td>${teacher.lastName}</td>
                <td>${teacher.name}</td>
                <td>${teacher.nameShort}</td>
                <td>${teacher.email}</td>
                <td>${teacher.trainingTime}</td>
                <td>
                    <button data-id="${teacher.id}" class="btn btn-warning edit-btn">Bearbeiten</button>
                    <button data-id="${teacher.id}" class="btn btn-danger delete-btn">Löschen</button>
                </td>
            `;

            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error('Fehler:', error);
    }
}

// Methode zum hinzufügen eines Lehrers
document.getElementById("teacherTableBody").addEventListener("click", function(event) {
    if (event.target.classList.contains("edit-btn")) {
        const id = event.target.getAttribute("data-id");
        openEditModal(id);
    }
});

async function openEditModal(id) {
    try {
        const response = await fetch(`/api/v1/teacher/${id}`);
        if (!response.ok) {
            throw new Error("Fehler beim Abrufen der Lehrerdaten");
        }
        const teacher = await response.json();

        // Werte in die Eingabefelder des Modals setzen
        document.getElementById("editTeacherId").value = teacher.id;
        document.getElementById("editLastName").value = teacher.lastName;
        document.getElementById("editName").value = teacher.name;
        document.getElementById("editNameShort").value = teacher.nameShort;
        document.getElementById("editEmail").value = teacher.email;
        document.getElementById("editTrainingTime").value = teacher.trainingTime;

        // Bootstrap Modal öffnen
        let modal = new bootstrap.Modal(document.getElementById("editTeacherModal"));
        modal.show();
    } catch (error) {
        console.error("Fehler:", error);
    }
}

document.getElementById("saveTeacherChanges").addEventListener("click", async function() {
    const id = document.getElementById("editTeacherId").value;
    const updatedTeacher = {
        lastName: document.getElementById("editLastName").value,
        name: document.getElementById("editName").value,
        nameShort: document.getElementById("editNameShort").value,
        email: document.getElementById("editEmail").value,
        trainingTime: document.getElementById("editTrainingTime").value
    };

    try {
        const response = await fetch(`/api/v1/teacher/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedTeacher)
        });

        if (!response.ok) {
            throw new Error("Fehler beim Aktualisieren des Lehrers");
        }

        alert("Lehrer erfolgreich aktualisiert!");
        let modal = bootstrap.Modal.getInstance(document.getElementById("editTeacherModal"));
        modal.hide();
        fetchTeachers(); // Tabelle neu laden
    } catch (error) {
        console.error("Fehler:", error);
    }
});

document.getElementById("teacherTableBody").addEventListener("click", function(event) {
    if (event.target.classList.contains("delete-btn")) {
        const id = event.target.getAttribute("data-id");
        deleteTeacher(id);
    }
});

// Methode zum löschen eines Lehrers
async function deleteTeacher(id) {

    if (!confirm("Möchtest du diesen Lehrer wirklich löschen?")) {
        return;
    }

    try {
        const response = await fetch(`/api/v1/teacher`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });

        if (!response.ok) {
            throw new Error("Fehler beim Löschen des Lehrers");
        }

        // Tabelle aktualisieren
        fetchTeachers();
    } catch (error) {
        console.error("Fehler:", error);
    }
}

//Modal für das Hinzufügen eines Lehrers
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("submitTeacher").addEventListener("click", function() {
        const teacher = {
            lastName: document.getElementById("lastName").value,
            name: document.getElementById("name").value,
            nameShort: document.getElementById("nameShort").value,
            email: document.getElementById("email").value,
            trainingTime: document.getElementById("trainingTime").value
        };

        console.log(teacher);

        fetch("http://localhost:8080/api/v1/teacher", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(teacher)
        })
            .then(response => {
                if (response.ok) {
                    alert("Lehrer erfolgreich hinzugefügt!");
                    document.getElementById("teacherForm").reset();
                    let modal = bootstrap.Modal.getInstance(document.getElementById("teacherModal"));
                    modal.hide();
                    location.reload();
                } else {
                    alert("Fehler beim Hinzufügen!");
                }
            })
            .catch(error => console.error("Fehler:", error));
    });
})


// Beim Laden der Seite die Lehrer-Daten abrufen
window.onload = fetchTeachers;
