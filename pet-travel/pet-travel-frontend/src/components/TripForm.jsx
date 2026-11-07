
import { useState } from "react";

function TripForm({ onCreateTrip }) {
  const [formData, setFormData] = useState({
    originCountry: "Ireland",
    destinationCountry: "Brazil",
    petType: "Dog",
    airline: "",
    departureDate: "",
  });

  function handleChange(event) {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  }

  function handleSubmit(event) {
    event.preventDefault();

    onCreateTrip(formData);
  }

  return (
    <form onSubmit={handleSubmit} className="trip-form">
      <h2>Create a Trip</h2>

      <label htmlFor="originCountry">Origin country</label>
      <select
        id="originCountry"
        name="originCountry"
        value={formData.originCountry}
        onChange={handleChange}
        required
      >
        <option value="Ireland">Ireland</option>
        <option value="Brazil">Brazil</option>
      </select>

      <label htmlFor="destinationCountry">
        Destination country
      </label>
      <select
        id="destinationCountry"
        name="destinationCountry"
        value={formData.destinationCountry}
        onChange={handleChange}
        required
      >
        <option value="Brazil">Brazil</option>
        <option value="Ireland">Ireland</option>
      </select>

      <label htmlFor="petType">Pet type</label>
      <select
        id="petType"
        name="petType"
        value={formData.petType}
        onChange={handleChange}
        required
      >
        <option value="Dog">Dog</option>
        <option value="Cat">Cat</option>
      </select>

      <label htmlFor="airline">Airline</label>
      <select
        id="airline"
        name="airline"
        value={formData.airline}
        onChange={handleChange}
        required
      >
        <option value="">Select an airline</option>
        <option value="TAP Air Portugal">TAP Air Portugal</option>
        <option value="LATAM">LATAM</option>
        <option value="Aer Lingus">Aer Lingus</option>
      </select>

      <label htmlFor="departureDate">
        Departure date and time
      </label>
      <input
        id="departureDate"
        type="datetime-local"
        name="departureDate"
        value={formData.departureDate}
        onChange={handleChange}
        required
      />

      <button type="submit">Create Trip</button>
    </form>
  );
}

export default TripForm;
