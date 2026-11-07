
function TaskList({ tasks, onToggleTask }) {
  if (tasks.length === 0) {
    return <p>No tasks yet. Create a trip to see requirements.</p>;
  }

  return (
    <section className="task-list">
      <h2>Travel Requirements</h2>

      {tasks.map((task) => (
        <div key={task.id} className="task-item">
          <label>
            <input
              type="checkbox"
              checked={task.completed}
              onChange={() => onToggleTask(task.id)}
            />

            <span
              style={{
                textDecoration: task.completed
                  ? "line-through"
                  : "none",
              }}
            >
              {task.title}
            </span>
          </label>

          {task.deadline && (
            <p className="task-deadline">
              Deadline: {task.deadline}
            </p>
          )}
        </div>
      ))}
    </section>
  );
}

export default TaskList;
