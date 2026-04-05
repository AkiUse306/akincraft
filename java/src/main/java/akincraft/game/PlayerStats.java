package akincraft.game;

/**
 * Health and hunger management system
 */
public class PlayerStats {
    private float health = 20.0f;
    private float maxHealth = 20.0f;
    private float hunger = 20.0f;
    private float maxHunger = 20.0f;
    private float exhaustion = 0.0f;
    private float saturation = 5.0f;
    
    private float damageInvulnerability = 0.0f;
    private static final float INVULN_TIME = 0.5f;
    
    public void update(float deltaTime) {
        // Hunger decreases over time (faster when moving)
        exhaustion += deltaTime * 0.05f; // Passive drain
        
        if (exhaustion >= 1.0f) {
            exhaustion -= 1.0f;
            hunger -= 0.1f;
        }
        
        // Passive health regeneration if well-fed
        if (hunger >= 18.0f && health < maxHealth) {
            health = Math.min(health + 0.1f * deltaTime, maxHealth);
        }
        
        // Starvation damage
        if (hunger <= 0.0f && health > 0.5f) {
            health -= 0.1f * deltaTime;
        }
        
        hunger = Math.max(0, Math.min(hunger, maxHunger));
        health = Math.max(0, Math.min(health, maxHealth));
        
        damageInvulnerability = Math.max(0, damageInvulnerability - deltaTime);
    }
    
    public void takeDamage(float amount) {
        if (damageInvulnerability <= 0) {
            health -= amount;
            damageInvulnerability = INVULN_TIME;
            if (health < 0) health = 0;
        }
    }
    
    public void eat(String food) {
        // Different foods restore different amounts
        switch (food) {
            case "apple":
                hunger += 4;
                saturation += 2;
                break;
            case "bread":
                hunger += 5;
                saturation += 6;
                break;
            case "cooked_beef":
                hunger += 8;
                saturation += 12;
                break;
            case "cooked_pork":
                hunger += 8;
                saturation += 12;
                break;
            case "cooked_chicken":
                hunger += 6;
                saturation += 7;
                break;
            case "golden_apple":
                hunger += 4;
                saturation += 10;
                health = Math.min(health + 4, maxHealth);
                break;
        }
        hunger = Math.min(hunger, maxHunger);
        saturation = Math.min(saturation, maxHunger);
    }
    
    public float getHealth() { return health; }
    public float getHunger() { return hunger; }
    public float getMaxHealth() { return maxHealth; }
    public float getMaxHunger() { return maxHunger; }
    public boolean isAlive() { return health > 0; }
    public boolean canAttack() { return damageInvulnerability <= 0; }
}
