
import { useState } from "react";
import TripForm from "./components/TripForm";
import TaskList from "./components/TaskList";
import "./App.css";

function App() {
  const [trip, setTrip] = useState(null);
  const [tasks, setTasks] = useState([]);

  function handleCreateTrip(tripData) {
    setTrip(tripData);

    // Temporary sample tasks.
    // Spring Boot will generate the real tasks later.
    setTasks([
      {
        id: 1,
        title: "Check rabies vaccination",
        deadline: "",
        completed: false,
      },
      {
        id: 2,
        title: "Obtain veterinary health certificate",
        deadline: "",
        completed: false,
      },
      {
        id: 3,
        title: "Confirm airline pet requirements",
        deadline: "",
        completed: false,
      },
    ]);
  }

  function handleToggleTask(taskId) {
    setTasks((previousTasks) =>
      previousTasks.map((task) =>
        task.id === taskId
          ? { ...task, completed: !task.completed }
          : task
      )
    );
  }

  return (
    <main className="app">
      <h1>Pet Travel</h1>
      <p>Plan your pet's international journey.</p>

      <TripForm onCreateTrip={handleCreateTrip} />

      {trip && (
        <section className="trip-summary">
          <h2>Your Trip</h2>
          <p>
            {trip.originCountry} → {trip.destinationCountry}
          </p>
          <p>Pet: {trip.petType}</p>
          <p>Airline: {trip.airline}</p>
          <p>Departure: {trip.departureDate}</p>
        </section>
      )}

      <TaskList
        tasks={tasks}
        onToggleTask={handleToggleTask}
      />
    </main>
  );
}

export default App;
