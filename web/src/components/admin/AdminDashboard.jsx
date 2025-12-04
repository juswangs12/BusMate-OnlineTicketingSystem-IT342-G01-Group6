import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../../../styles/AdminDashboard.css';

const AdminDashboard = () => {
  const navigate = useNavigate();

  const handleNavigate = (path) => {
    navigate(path);
  };

  return (
    <div className="admin-dashboard-page">
      {/* Header Section */}
      <div className="admin-dashboard-header">
        <div>
          <h1>Admin Dashboard</h1>
          <p>Manage your bus operations</p>
        </div>
        <button className="btn btn-outlined">Back to Home</button>
      </div>

      {/* Stats Cards */}
      <div className="admin-stats-grid">
        <div className="admin-stat-card">
          <div className="stat-icon bookings-icon"></div>
          <div>
            <div className="label">Total Bookings</div>
            <h2>1,247</h2>
          </div>
        </div>
        <div className="admin-stat-card">
          <div className="stat-icon buses-icon"></div>
          <div>
            <div className="label">Active Buses</div>
            <h2>23</h2>
          </div>
        </div>
        <div className="admin-stat-card">
          <div className="stat-icon routes-icon"></div>
          <div>
            <div className="label">Total Routes</div>
            <h2>15</h2>
          </div>
        </div>
        <div className="admin-stat-card revenue">
          <div className="stat-icon revenue-icon"></div>
          <div>
            <div className="label">Revenue (MTD)</div>
            <h2>₱487,500</h2>
          </div>
        </div>
      </div>

      {/* Action Cards Grid */}
      <div className="admin-actions-grid">
        <button 
          className="admin-action-card"
          onClick={() => handleNavigate('/admin/buses')}
        >
          <div className="action-icon buses-icon-large"></div>
          <h3>Manage Buses</h3>
          <p>Add, edit, or remove buses</p>
        </button>
        <button 
          className="admin-action-card"
          onClick={() => handleNavigate('/admin/routes')}
        >
          <div className="action-icon routes-icon-large"></div>
          <h3>Manage Routes</h3>
          <p>Configure bus routes</p>
        </button>
        <button 
          className="admin-action-card"
          onClick={() => handleNavigate('/admin/schedules')}
        >
          <div className="action-icon schedules-icon-large"></div>
          <h3>Manage Schedules</h3>
          <p>Set departure times</p>
        </button>
        <button 
          className="admin-action-card"
          onClick={() => handleNavigate('/admin/reports')}
        >
          <div className="action-icon reports-icon-large"></div>
          <h3>Reports</h3>
          <p>View analytics & reports</p>
        </button>
      </div>
    </div>
  );
};

export default AdminDashboard;