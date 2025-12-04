import AuthPanel from '../AuthPanel/AuthPanel'
import QuickStartCard from '../QuickStartCard/QuickStartCard'
import styles from './AuthPage.module.css'

// CHANGE: Use your local image from the public folder
const heroImage = '/images/bus-bg.png'

const features = [
  {
    title: 'Real-Time Availability',
    description: 'See live seat availability and pick your preferred spot.',
  },
  {
    title: 'Secure Payment Options',
    description: 'Pay confidently with GoTyme and 256-bit encryption.',
  },
  {
    title: 'Instant E-Tickets',
    description: 'Receive QR-coded tickets straight to your inbox.',
  },
]

function AuthPage({ onAuthenticated }) {
  return (
    <div className={styles.wrapper}>
      {/* The background image is applied here via inline styles */}
      <div className={styles.background} style={{ backgroundImage: `url(${heroImage})` }} />
      <div className={styles.overlay} />
      <div className={styles.content}>
        <section className={styles.hero}>
          <div className={styles.brand}>
            <div className={styles.brandIcon}>🚍</div>
            <div>
              <p className={styles.brandName}>BusMate</p>
              <p className={styles.brandTagline}>Smart Travel, Easy Booking</p>
            </div>
          </div>
          <h1>Your Journey Starts Here</h1>
          <p className={styles.heroSubtitle}>
            Book intercity bus tickets instantly with real-time seat selection, secure payments, and contactless
            e-tickets.
          </p>
          <ul className={styles.featureList}>
            {features.map((feature) => (
              <li key={feature.title}>
                <span>✓</span>
                <div>
                  <p>{feature.title}</p>
                  <small>{feature.description}</small>
                </div>
              </li>
            ))}
          </ul>
        </section>
        <section className={styles.authColumn}>
          <AuthPanel onAuthenticated={onAuthenticated} />
          <QuickStartCard />
        </section>
      </div>
    </div>
  )
}

export default AuthPage