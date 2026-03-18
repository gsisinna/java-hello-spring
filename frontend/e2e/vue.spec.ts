import { test, expect } from '@playwright/test'

test('visits the app root url', async ({ page }) => {
  await page.goto('/')
  await expect(page.locator('h1')).toContainText('Explore Java objects')
  await expect(page.getByRole('button', { name: 'Work on student endpoints' })).toBeVisible()
})
